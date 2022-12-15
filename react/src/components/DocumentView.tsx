import { Box, Button, Card, Typography } from '@mui/material';
import { useDeleteDocumentMutation, useGetDocumentsQuery } from '../api';
import DocumentForm from './DocumentForm';

function DocumentView() {
  const { data: documents } = useGetDocumentsQuery();
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
          <Card variant='outlined' key={document.id}>
            <Typography variant='h4'>{document.title}</Typography>
            {document.author.length > 0 && (
              <Typography>by {document.author}</Typography>
            )}
            <Button onClick={() => deleteDocument(document.id)}>Remove</Button>
          </Card>
        ))}
      </Box>
    </Box>
  );
}

export default DocumentView;
