import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { Document, Postings } from './types';

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
    deleteDocument: builder.mutation<boolean, string>({
      query: (id) => ({
        url: `document/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: ['documents'],
    }),
    getWord: builder.query<Postings, string>({
      query: (word) => `word/${word}`,
      providesTags: ['documents'],
    }),
  }),
});

export const {
  useGetDocumentsQuery,
  useAddDocumentMutation,
  useDeleteDocumentMutation,
  useLazyGetWordQuery,
} = api;
