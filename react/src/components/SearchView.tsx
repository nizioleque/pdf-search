import { Search } from '@mui/icons-material';
import { Box, InputAdornment, TextField } from '@mui/material';
import { useEffect, useState } from 'react';
import { useGetDocumentsQuery, useLazyGetWordQuery } from '../api';
import SearchResultCard from './SearchResultCard';

function SearchView() {
  const { data: documents } = useGetDocumentsQuery();
  const [getWord, { data: searchResults }] = useLazyGetWordQuery();
  const [showResult, setShowResults] = useState<boolean>(false);
  const [searchQuery, setSearchQuery] = useState<string>('');

  useEffect(() => {
    setShowResults(searchQuery.length > 0);
    if (searchQuery.length > 0) getWord(searchQuery);
  }, [searchQuery]);

  return (
    <Box
      sx={{
        margin: '0 auto',
      }}
    >
      <TextField
        sx={{
          width: 500,
        }}
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
      {searchResults &&
        documents &&
        showResult &&
        searchResults.map((searchResult) => (
          <SearchResultCard
            searchResult={searchResult}
            searchQuery={searchQuery}
          />
        ))}
    </Box>
  );
}

export default SearchView;
