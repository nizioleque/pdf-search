import { Button, Card, TextField, Typography } from '@mui/material';
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
  };

  return (
    <Card variant='outlined'>
      <Typography variant='h3'>Add document</Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label='Title'
          value={title}
          onChange={(event) => setTitle(event.target.value)}
        />
        <TextField
          label='Author'
          value={author}
          onChange={(event) => setAuthor(event.target.value)}
        />
        <Button variant='contained' component='label'>
          Upload
          <input
            hidden
            type='file'
            accept='.pdf'
            onChange={(event) =>
              event.target.files?.[0] && setFile(event.target.files[0])
            }
          />
        </Button>
        <Button type='submit'>Add</Button>
      </form>
    </Card>
  );
}

export default DocumentForm;
