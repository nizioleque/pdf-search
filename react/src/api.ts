import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { Document } from './types';

export const api = createApi({
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),
  tagTypes: ['documents'],
  endpoints: (builder) => ({
    getDocuments: builder.query<Document[], void>({
      query: () => 'document',
      providesTags: ['documents'],
    }),
    addDocument: builder.mutation<Document, FormData>({
      query: (body) => ({
        url: `document`,
        method: 'POST',
        body,
      }),
      invalidatesTags: ['documents'],
    }),
  }),
});

export const { useGetDocumentsQuery, useAddDocumentMutation } = api;
