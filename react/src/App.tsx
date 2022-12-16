import { Box, createTheme, CssBaseline, ThemeProvider } from '@mui/material';
import { ApiProvider } from '@reduxjs/toolkit/dist/query/react';
import { api } from './api';
import DocumentView from './components/DocumentView';
import SearchView from './components/SearchView';

const theme = createTheme({
  palette: {
    primary: {
      main: '#0039cb',
    },
  },
  shape: {
    borderRadius: 15,
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <ApiProvider api={api}>
        <CssBaseline />
        <Box
          sx={{
            display: 'grid',
            grid: 'auto / 1fr 520px',
            height: '100%',
            backgroundColor: '#f8f8f8',
          }}
        >
          <SearchView />
          <DocumentView />
        </Box>
      </ApiProvider>
    </ThemeProvider>
  );
}

export default App;
