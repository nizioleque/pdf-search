import { useGetDocumentsQuery } from '../api';
import DocumentForm from './DocumentForm';

function DocumentView() {
  const { data: documents } = useGetDocumentsQuery();

  if (!documents) return <div>Loading...</div>;

  return (
    <>
      <h1>Documents:</h1>
      <DocumentForm />
      {documents.map((document) => (
        <h4 key={document.id}>{JSON.stringify(document, null, 2)}</h4>
      ))}
    </>
  );
}

export default DocumentView;
