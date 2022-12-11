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
    // console.log(...formData);
    // for (var [key, value] of formData.entries()) {
    //   console.log(key, value);
    // }
    addDocument(formData);
  };

  return (
    <>
      <h2>Add document:</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title</label>
          <input
            type='text'
            value={title}
            onChange={(event) => setTitle(event.target.value)}
          />
        </div>
        <div>
          <label>Author</label>

          <input
            type='text'
            value={author}
            onChange={(event) => setAuthor(event.target.value)}
          />
        </div>
        <div>
          <label>File</label>
          <input
            type='file'
            accept='.pdf'
            onChange={(event) =>
              event.target.files?.[0] && setFile(event.target.files[0])
            }
          />
        </div>
        <input type='submit' value='Add' />
      </form>
    </>
  );
}

export default DocumentForm;
