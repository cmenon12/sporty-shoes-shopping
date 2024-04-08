function filterTable(singular, plural) {

  // get the filter values and the rows
  const category = document.getElementById('inputCategory')
      ? document.getElementById('inputCategory').value : 'none';
  const user = document.getElementById('inputUser')
      ? document.getElementById('inputUser').value : 'none';
  const search = document.getElementById('inputSearch')
      ? document.getElementById('inputSearch').value.toLowerCase() : '';
  const startDate = document.getElementById('inputStartDate') ? Math.floor(
      Date.parse(document.getElementById('inputStartDate').value) / 1000) : 0;
  const endDate = document.getElementById('inputEndDate') ? Math.floor(
          Date.parse(document.getElementById('inputEndDate').value) / 1000)
      : Number.MAX_SAFE_INTEGER;
  const rows = document.getElementById('tableToFilter').getElementsByTagName(
      'tr');

  let count = 0;
  for (let i = 0; i < rows.length; i++) {

    // Get the values for that row
    // Only rowDate has a default because when present, the date value is always set
    const row = rows[i];
    const rowCategory = row.hasAttribute('data-category') ? row.getAttribute(
        'data-category').split(', ') : null
    const rowUser = row.hasAttribute('data-user') ? row.getAttribute(
        'data-user') : null;
    const rowSearch = row.getAttribute('data-search');
    const rowDate = row.hasAttribute('data-date') ? Math.floor(
        Date.parse(row.getAttribute('data-date')) / 1000) : 1000000;

    // Ignore this row if it has no attributes
    if (!rowCategory && !rowUser && !rowSearch && !row.hasAttribute(
        'data-date')) {
      continue;
    }

    // Check if the row should be displayed
    let display;
    if ((category === 'none' || rowCategory.includes(category))
        && (user === 'none' || rowUser === user)
        && (search === '' || rowSearch.includes(search))
        && (rowDate >= startDate && rowDate <= endDate)) {
      display = '';
      count++;
    } else {
      display = 'none';
    }

    // Apply the display value
    // If the row has a data-rows-above attribute, then apply to those rows too
    const rowsAbove = parseInt(
        row.hasAttribute('data-rows-above') ? row.getAttribute(
            'data-rows-above') : 0);
    for (let j = i - rowsAbove; j <= i; j++) {
      rows[j].style.display = display;
    }
  }

  // Set the caption
  document.getElementById('tableToFilter').getElementsByTagName(
      'caption')[0].innerText = count
      + ' ' + (count === 1 ? singular : plural);

}