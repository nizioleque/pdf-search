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
  )[0];

  if (!thisDocument) return null;

  const queryText = searchQuery.trim().split(' ').slice(0, -1).join(' ');

  return (
    <Card
      key={`${searchResult.documentId}+${searchResult.pageNumber}+${searchResult.firstWordIndex}`}
      variant='outlined'
      sx={{
        width: 400,
        p: 2,
      }}
    >
      <Typography fontSize='1.2rem'>
        <Box component='span' fontWeight={500}>
          {thisDocument.title}
        </Box>
        {thisDocument.author.length > 0 && (
          <Box component='span' fontSize='1rem'>
            <Box component='span'>{' by '}</Box>
            <Box component='span' fontStyle='italic'>
              {thisDocument.author}
            </Box>
          </Box>
        )}
      </Typography>
      <Typography fontSize='0.8rem'>
        {`page ${searchResult.pageNumber}, from word ${
          searchResult.firstWordIndex + 1
        }`}
      </Typography>
      <Typography mt={1}>
        {'...'}
        <Box
          component='span'
          fontWeight={500}
        >{`${queryText} ${searchResult.lastWord}`}</Box>
        {'...'}
      </Typography>
    </Card>
  );
}

export default SearchResultCard;
