import { Container } from '@mui/material';
import { ApiProvider } from '@reduxjs/toolkit/dist/query/react';
import { api } from './api';
import DocumentView from './components/DocumentView';
import SearchView from './components/SearchView';

function App() {
  return (
    <ApiProvider api={api}>
      <Container maxWidth='md'>
        App
        <DocumentView />
        <SearchView/>
      </Container>
    </ApiProvider>
  );
}

export default App;
