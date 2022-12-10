export interface Document {
  id: string;
  title: string;
  author: string;
}

export interface Word {
  id: string;
  word: string;
  postings: Posting[];
}

export interface Posting {
  documentId: string;
  pageNumbers: number[];
}
