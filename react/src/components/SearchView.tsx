import { useGetDocumentsQuery, useLazyGetWordQuery } from '../api';

function SearchView() {
  const { data: documents } = useGetDocumentsQuery();
  const [getWord, { data: searchResult }] = useLazyGetWordQuery();

  return (
    <>
      <h1>Search</h1>
      <input type='search' onChange={(event) => getWord(event.target.value)} />
      {searchResult && documents && (
        <>
          <h2>Results:</h2>
          {Object.entries(searchResult).map(([key, value]) => (
            <div key={key}>
              <h4>
                {JSON.stringify(
                  documents.find((document) => document.id === key),
                  null,
                  2
                )}
              </h4>
              <p>{JSON.stringify(value, null, 2)}</p>
            </div>
          ))}
        </>
      )}
    </>
  );
}

export default SearchView;
