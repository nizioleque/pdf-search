import { Card, Typography } from '@mui/material';
import { useGetDocumentsQuery } from '../api';
import { SearchResult } from '../types';

interface SearchResultProps {
  searchResult: SearchResult;
  searchQuery: string;
}

function SearchResultCard({ searchResult, searchQuery }: SearchResultProps) {
  const { data: documents } = useGetDocumentsQuery();
  const thisDocument = documents?.filter(
    (document) => document.id === searchResult.documentId
  )[0]!;

  const queryText = searchQuery.trim().split(' ').slice(0, -1).join(' ');
  console.log(searchQuery, queryText);

  return (
    <Card
      key={`${searchResult.documentId}+${searchResult.pageNumber}+${searchResult.firstWordIndex}`}
    >
      <Typography variant='h5'>{`${thisDocument.title}${
        thisDocument.author.length > 0 && ` by ${thisDocument.author}`
      }, page ${searchResult.pageNumber}, from word ${
        searchResult.firstWordIndex + 1
      }`}</Typography>
      <Typography>{`...${queryText} ${searchResult.lastWord}...`}</Typography>
    </Card>
  );
}

export default SearchResultCard;
