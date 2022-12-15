import { useGetDocumentsQuery, useLazyGetWordQuery } from '../api';

function SearchView() {
  const { data: documents } = useGetDocumentsQuery();
  const [getWord, { data: searchResults }] = useLazyGetWordQuery();

  return (
    <>
      <h1>Search</h1>
      <input type='search' onChange={(event) => getWord(event.target.value)} />
      {searchResults && documents && (
        <>
          <h2>Results:</h2>
          {searchResults.map((searchResult) => (
            <div
              key={`${searchResult.documentId}+${searchResult.pageNumber}+${searchResult.firstWordIndex}`}
            >
              <h4>
                {JSON.stringify(
                  documents.find(
                    (document) => document.id === searchResult.documentId
                  ),
                  null,
                  2
                )}
              </h4>
              <p>{JSON.stringify(searchResult, null, 2)}</p>
            </div>
          ))}
        </>
      )}
    </>
  );
}

export default SearchView;
