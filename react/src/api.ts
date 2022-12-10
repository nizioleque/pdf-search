import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { Document } from './types';

export const api = createApi({
  baseQuery: fetchBaseQuery({ baseUrl: 'http://localhost:8080/api/' }),
  endpoints: (builder) => ({
    getDocuments: builder.query<Document[], void>({
      query: () => 'document',
    }),
  }),
});

export const { useGetDocumentsQuery } = api;
