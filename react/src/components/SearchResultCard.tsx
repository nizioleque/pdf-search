import { Box, Card, Typography } from '@mui/material';
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
      variant='outlined'
    >
      <Typography fontSize='1.2rem'>
        <Box component='span' fontWeight={700}>
          {thisDocument.title}
        </Box>
        {`${thisDocument.author.length > 0 && ` by ${thisDocument.author}`}`}
      </Typography>
      <Typography fontSize='0.9rem'>
        {`page ${searchResult.pageNumber}, from word ${
          searchResult.firstWordIndex + 1
        }`}
      </Typography>
      <Typography>{`...${queryText} ${searchResult.lastWord}...`}</Typography>
    </Card>
  );
}

export default SearchResultCard;
