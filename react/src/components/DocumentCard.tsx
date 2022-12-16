import { Delete, DeleteOutline } from '@mui/icons-material';
import {
  Box,
  Card,
  Typography,
  Button,
  CircularProgress,
  IconButton,
} from '@mui/material';
import { useDeleteDocumentMutation } from '../api';
import { Document, DocumentStatus } from '../types';

interface DocumentCardProps {
  document: Document;
}

function DocumentCard({ document }: DocumentCardProps) {
  const [deleteDocument, {}] = useDeleteDocumentMutation();

  return (
    <Card
      variant='outlined'
      sx={{
        display: 'grid',
        p: 2,
      }}
    >
      <Box
        sx={{
          gridRow: 1,
          gridColumn: 1,
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <Box>
          <Typography fontWeight={500}>{document.title}</Typography>
          {document.author.length > 0 && (
            <Typography mt={0.5} fontSize='0.9rem'>
              by{' '}
              <Box component='span' fontStyle='italic'>
                {document.author}
              </Box>
            </Typography>
          )}
        </Box>
        <IconButton onClick={() => deleteDocument(document.id)} edge='end'>
          <DeleteOutline />
        </IconButton>
      </Box>
      {document.status === DocumentStatus.ADDING && (
        <Box
          sx={{
            gridRow: 1,
            gridColumn: 1,
            zIndex: 1,
            background: 'rgb(255, 255, 255, 0.8)',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            gap: 2,
          }}
        >
          <CircularProgress />
          <Typography>Adding...</Typography>
        </Box>
      )}
    </Card>
  );
}

export default DocumentCard;
