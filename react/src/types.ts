export interface Document {
  id: string;
  title: string;
  author: string;
}

export interface Postings {
  [key: string]: number[];
}
