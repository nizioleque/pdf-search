export interface Document {
  id: string;
  title: string;
  author: string;
}

export interface SearchResult {
  documentId: string;
  pageNumber: number;
  firstWordIndex: number;
  lastWord: string;
}
