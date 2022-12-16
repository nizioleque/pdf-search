import { Box, Card, Typography } from '@mui/material';
import { useGetDocumentsQuery } from '../api';
import DocumentCard from './DocumentCard';
import DocumentForm from './DocumentForm';

function DocumentView() {
  const { data: documents } = useGetDocumentsQuery(undefined, {
    pollingInterval: 1000,
  });

  if (!documents) return <div>Loading...</div>;

  return (
    <Card
      sx={{
        height: '100%',
        px: 2,
        overflow: 'auto',
      }}
      square
      elevation={5}
    >
      <Typography
        sx={(theme) => ({
          color: theme.palette.primary.main,
          textAlign: 'center',
          fontSize: '1.7rem',
          fontWeight: 500,
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
            <DocumentCard document={document} key={document.id} />
          ))}
        </Box>
      </Box>
    </Card>
  );
}

export default DocumentView;
