const boardSize = 8;
let board = [];

function initGame() {
  board = [];
  for (let y = 0; y < boardSize; y++) {
    let row = [];
    for (let x = 0; x < boardSize; x++) {
      row.push(getRandomTile());
    }
    board.push(row);
  }
  drawBoard();
}

function drawBoard() {
  let gameBoard = document.getElementById('game-board');
  gameBoard.innerHTML = '';
  for (let y = 0; y < boardSize; y++) {
    for (let x = 0; x < boardSize; x++) {
      let tile = document.createElement('div');
      tile.className = 'tile';
      tile.textContent = board[y][x];
      gameBoard.appendChild(tile);
    }
  }
}

function getRandomTile() {
  return Math.floor(Math.random() * 4) + 1;
}

function checkMatches() {
  for (let y = 0; y < boardSize; y++) {
    for (let x = 0; x < boardSize - 2; x++) {
      if (board[y][x] === board[y][x + 1] && board[y][x] === board[y][x + 2]) {
        // horizontal match detected
        removeTiles(x, y, true);
      }
    }
  }

  for (let x = 0; x < boardSize; x++) {
    for (let y = 0; y < boardSize - 2; y++) {
      if (board[y][x] === board[y + 1][x] && board[y][x] === board[y + 2][x]) {
        // vertical match detected
        removeTiles(x, y, false);
      }
    }
  }
}

function removeTiles(x, y, horizontal) {
  if (horizontal) {
    for (let i = 0; i < 3; i++) {
      board[y][x + i] = getRandomTile();
    }
  } else {
    for (let i = 0; i < 3; i++) {
      for (let j = y + i; j > 0; j--) {
        board[j][x] = board[j - 1][x];  // move tiles down
      }
      board[0][x] = getRandomTile();  // add new tile at the top
    }
  }
}
let selectedTile = null;

function drawBoard() {
  let gameBoard = document.getElementById('game-board');
  gameBoard.innerHTML = '';
  for (let y = 0; y < boardSize; y++) {
    for (let x = 0; x < boardSize; x++) {
      let tile = document.createElement('div');
      tile.className = 'tile';
      tile.textContent = board[y][x];
      tile.onclick = function() { selectTile(x, y); };
      gameBoard.appendChild(tile);
    }
  }
}

function selectTile(x, y) {
  if (selectedTile) {
    // check if the clicked tile is next to the selected one
    if ((Math.abs(selectedTile.x - x) === 1 && selectedTile.y === y) ||
        (Math.abs(selectedTile.y - y) === 1 && selectedTile.x === x)) {
      swapTiles(selectedTile.x, selectedTile.y, x, y);
      selectedTile = null;  // reset the selected tile
    } else {
      selectedTile = {x: x, y: y};  // select a new tile
    }
  } else {
    selectedTile = {x: x, y: y};  // select a tile
  }
}

function swapTiles(x1, y1, x2, y2) {
  let temp = board[y1][x1];
  board[y1][x1] = board[y2][x2];
  board[y2][x2] = temp;
  drawBoard();  // redraw the board to reflect the change
  checkMatches();  // check for matches after swapping
  drawBoard();  // redraw the board again to show the removed matches
}

function drawBoard() {
  let gameBoard = document.getElementById('game-board');
  gameBoard.innerHTML = '';
  for (let y = 0; y < boardSize; y++) {
    for (let x = 0; x < boardSize; x++) {
      let tile = document.createElement('div');
      tile.className = 'tile';
      tile.textContent = board[y][x];
      tile.onclick = function() { selectTile(x, y); };
      gameBoard.appendChild(tile);
    }
  }
}

function selectTile(x, y) {
  if (selectedTile) {
    // check if the clicked tile is next to the selected one
    if ((Math.abs(selectedTile.x - x) === 1 && selectedTile.y === y) ||
        (Math.abs(selectedTile.y - y) === 1 && selectedTile.x === x)) {
      swapTiles(selectedTile.x, selectedTile.y, x, y);
      selectedTile = null;  // reset the selected tile
    } else {
      selectedTile = {x: x, y: y};  // select a new tile
    }
  } else {
    selectedTile = {x: x, y: y};  // select a tile
  }
}

function swapTiles(x1, y1, x2, y2) {
  let temp = board[y1][x1];
  board[y1][x1] = board[y2][x2];
  board[y2][x2] = temp;
  drawBoard();  // redraw the board to reflect the change
  checkMatches();  // check for matches after swapping
  drawBoard();  // redraw the board again to show the removed matches
}
function drawBoard() {
  let gameBoard = document.getElementById('game-board');
  gameBoard.innerHTML = '';
  for (let y = 0; y < boardSize; y++) {
    for (let x = 0; x < boardSize; x++) {
      let tile = document.createElement('div');
      tile.className = 'tile';
      tile.textContent = board[y][x];
      if (selectedTile && selectedTile.x === x && selectedTile.y === y) {
        tile.classList.add('selected');
      }
      tile.onclick = function() { selectTile(x, y); };
      gameBoard.appendChild(tile);
    }
  }
}

function selectTile(x, y) {
  if (selectedTile) {
    // check if the clicked tile is next to the selected one
    if ((Math.abs(selectedTile.x - x) === 1 && selectedTile.y === y) ||
        (Math.abs(selectedTile.y - y) === 1 && selectedTile.x === x)) {
      swapTiles(selectedTile.x, selectedTile.y, x, y);
      selectedTile = null;  // reset the selected tile
    } else {
      selectedTile = {x: x, y: y};  // select a new tile
    }
  } else {
    selectedTile = {x: x, y: y};  // select a tile
  }
  drawBoard();  // redraw the board to reflect the selection
}


initGame();
