import { Box } from '@mui/material';
import { ApiProvider } from '@reduxjs/toolkit/dist/query/react';
import { api } from './api';
import DocumentView from './components/DocumentView';
import SearchView from './components/SearchView';

function App() {
  return (
    <ApiProvider api={api}>
      <Box sx={{ display: 'grid', grid: '1fr / 350px 1fr' }}>
        <DocumentView />
        <SearchView />
      </Box>
    </ApiProvider>
  );
}

export default App;
