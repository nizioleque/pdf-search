import { Container } from '@mui/material';
import { ApiProvider } from '@reduxjs/toolkit/dist/query/react';
import { api } from './api';
import DocumentView from './views/DocumentView';

function App() {
  return (
    <ApiProvider api={api}>
      <Container maxWidth='md'>
        App
        <DocumentView />
      </Container>
    </ApiProvider>
  );
}

export default App;
