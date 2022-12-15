import { Box, Button, Card, CircularProgress, Typography } from '@mui/material';
import { useDeleteDocumentMutation, useGetDocumentsQuery } from '../api';
import { DocumentStatus } from '../types';
import DocumentForm from './DocumentForm';

function DocumentView() {
  const { data: documents } = useGetDocumentsQuery(undefined, {
    pollingInterval: 1000,
  });
  const [deleteDocument, {}] = useDeleteDocumentMutation();

  if (!documents) return <div>Loading...</div>;

  return (
    <Box
      sx={(theme) => ({
        borderRight: '2px solid',
        borderColor: theme.palette.primary.main,
      })}
    >
      <Typography
        variant='h2'
        sx={(theme) => ({ backgroundColor: theme.palette.primary.main })}
      >
        Documents
      </Typography>
      <Box p={2}>
        <DocumentForm />
        {documents.map((document) => (
          <Card
            variant='outlined'
            key={document.id}
            sx={{
              display: 'grid',
            }}
          >
            <Box sx={{ gridRow: 1, gridColumn: 1 }}>
              <Typography variant='h4'>{document.title}</Typography>
              {document.author.length > 0 && (
                <Typography>by {document.author}</Typography>
              )}
              <Button onClick={() => deleteDocument(document.id)}>
                Remove
              </Button>
            </Box>
            {document.status === DocumentStatus.ADDING && (
              <Box
                sx={{
                  gridRow: 1,
                  gridColumn: 1,
                  zIndex: 1,
                  background: 'rgb(255, 255, 255, 0.85)',
                }}
              >
                <CircularProgress />
                <Typography>Adding...</Typography>
              </Box>
            )}
          </Card>
        ))}
      </Box>
    </Box>
  );
}

export default DocumentView;
