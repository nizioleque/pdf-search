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
    <Card
      sx={{
        height: '100%',
        px: 2,
      }}
      square
      elevation={8}
    >
      <Typography
        sx={(theme) => ({
          color: theme.palette.primary.main,
          textAlign: 'center',
          fontSize: '1.7rem',
          fontWeight: 700,
          py: 1,
        })}
      >
        Documents
      </Typography>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
        }}
      >
        <DocumentForm />
        <Box
          sx={{
            display: 'grid',
            gridTemplateColumns: '1fr 1fr',
            gap: 2,
          }}
        >
          {documents.map((document) => (
            <Card
              variant='outlined'
              key={document.id}
              sx={{
                display: 'grid',
              }}
            >
              <Box sx={{ gridRow: 1, gridColumn: 1 }}>
                <Typography>{document.title}</Typography>
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
    </Card>
  );
}

export default DocumentView;
