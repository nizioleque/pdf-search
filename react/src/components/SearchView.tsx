import { Search } from '@mui/icons-material';
import { Box, Card, InputAdornment, TextField } from '@mui/material';
import { useEffect, useState } from 'react';
import { useGetDocumentsQuery, useLazyGetWordQuery } from '../api';
import useDebounce from '../useDebounce';
import SearchResultCard from './SearchResultCard';

function SearchView() {
  const { data: documents } = useGetDocumentsQuery();
  const [getWord, { data: searchResults }] = useLazyGetWordQuery();
  const [showResult, setShowResults] = useState<boolean>(false);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const debouncedSearchQuery = useDebounce<string>(searchQuery, 500);

  useEffect(() => {
    setShowResults(debouncedSearchQuery.length > 0);
    if (debouncedSearchQuery.length > 0) getWord(debouncedSearchQuery);
  }, [debouncedSearchQuery]);

  return (
    <Box
      sx={{
        overflow: 'auto',
        width: '100%',
      }}
    >
      <Box
        sx={{
          margin: '0 auto',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          p: 2,
        }}
      >
        <Card sx={{ width: 500 }} elevation={3}>
          <TextField
            inputProps={{ sx: { border: 'none' } }}
            sx={{
              '& fieldset': { border: 'none' },
            }}
            fullWidth
            onChange={(event) => setSearchQuery(event.target.value)}
            placeholder='Search'
            InputProps={{
              startAdornment: (
                <InputAdornment position='start'>
                  <Search />
                </InputAdornment>
              ),
            }}
          />
        </Card>
        {searchResults && documents && showResult && (
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              mt: 4,
              gap: 2,
            }}
          >
            {searchResults.map((searchResult) => (
              <SearchResultCard
                searchResult={searchResult}
                searchQuery={searchQuery}
              />
            ))}
          </Box>
        )}
      </Box>
    </Box>
  );
}

export default SearchView;
