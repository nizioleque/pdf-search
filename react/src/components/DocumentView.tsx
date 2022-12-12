import { useDeleteDocumentMutation, useGetDocumentsQuery } from '../api';
import DocumentForm from './DocumentForm';

function DocumentView() {
  const { data: documents } = useGetDocumentsQuery();
  const [deleteDocument, {}] = useDeleteDocumentMutation();

  if (!documents) return <div>Loading...</div>;

  return (
    <>
      <h1>Documents:</h1>
      <DocumentForm />
      {documents.map((document) => (
        <div key={document.id}>
          <h4>{JSON.stringify(document, null, 2)}</h4>
          <button onClick={() => deleteDocument(document.id)}>Remove</button>
        </div>
      ))}
    </>
  );
}

export default DocumentView;
