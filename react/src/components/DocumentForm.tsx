import { Add, InsertDriveFile } from '@mui/icons-material';
import { Box, Button, Card, TextField, Typography } from '@mui/material';
import { FormEvent, useState } from 'react';
import { useAddDocumentMutation } from '../api';

function DocumentForm() {
  const [addDocument, {}] = useAddDocumentMutation();

  const [title, setTitle] = useState<string>('');
  const [author, setAuthor] = useState<string>('');
  const [file, setFile] = useState<File | null>(null);

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!file) return;
    const formData = new FormData();
    formData.append('title', title);
    formData.append('author', author);
    formData.append('file', file);
    addDocument(formData);
    setTitle('');
    setAuthor('');
    setFile(null);
  };

  return (
    <form onSubmit={handleSubmit}>
      <Card
        variant='outlined'
        sx={{
          p: 1,
          '& > *': {
            m: 1,
          },
        }}
      >
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
          <Typography
            sx={{
              textAlign: 'center',
              fontSize: '1.3rem',
            }}
          >
            Add document
          </Typography>
          <Button type='submit' variant='contained' startIcon={<Add />}>
            Add
          </Button>
        </Box>
        <Box>
          <TextField
            label='Title'
            value={title}
            onChange={(event) => setTitle(event.target.value)}
            fullWidth
            variant='standard'
          />
        </Box>
        <Box>
          <TextField
            label='Author'
            value={author}
            onChange={(event) => setAuthor(event.target.value)}
            fullWidth
            variant='standard'
          />
        </Box>
        <Box
          sx={{
            mt: 3,
            display: 'flex',
            alignItems: 'center',
            gap: 2,
          }}
        >
          <Button
            sx={{ flexShrink: 0 }}
            variant='outlined'
            component='label'
            startIcon={<InsertDriveFile />}
          >
            Select file
            <input
              hidden
              type='file'
              accept='.pdf'
              value={''}
              onChange={(event) =>
                event.target.files?.[0] && setFile(event.target.files[0])
              }
            />
          </Button>
          <Typography fontStyle='italic'>
            {file?.name ?? 'No file selected'}
          </Typography>
        </Box>
      </Card>
    </form>
  );
}

export default DocumentForm;
