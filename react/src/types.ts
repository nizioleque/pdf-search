export interface Document {
  id: string;
  title: string;
  author: string;
  status: DocumentStatus;
}

export enum DocumentStatus {
  ADDING = 'ADDING',
  ADDED = 'ADDED',
}

export interface SearchResult {
  documentId: string;
  pageNumber: number;
  firstWordIndex: number;
  lastWord: string;
}
