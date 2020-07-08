import {ChessState, RoomModule} from "./goBangEnum";
import {ChessPieceColor, PlayState} from "./goBangEnum";

/**
 * 下棋控制器
 */
class Controller {

    constructor(boardData, userInfo) {
        this.boardData = boardData;
        this.userInfo = userInfo;
    }

    /**
     * 落子
     * @param positionX
     * @param positionY
     * @returns {never|number|*|number}
     */
    placingPiece(positionX, positionY) {
        console.log("positionX: " + positionX + ", positionY: " + positionY)
        if (this.boardData.chessState != ChessState.start) {
            return this.boardData.chessState;
        }
        // 当前不该你下棋，并且不是自对弈模式
        if (this.boardData.currentTurn != this.userInfo.holderColor && this.boardData.roomModule != RoomModule.self) {
            return this.boardData.currentTurn;
        }
        let positionXY = this.boardData.pointers[positionX][positionY];
        if (positionXY != ChessPieceColor.empty) {
            return positionXY;
        }

        let pieceColor = this.userInfo.holderColor;
        if (this.boardData.roomModule === RoomModule.self) {
            pieceColor = this.boardData.currentTurn;
        }
        this.doPlacingPiece(positionX, positionY, pieceColor);
        // 判断胜负，如果胜负已定，通知玩家，结束本局
        let judge = this.win(positionX, positionY);
        if (judge === PlayState.win) {
            this.boardData.chessState = ChessState.end;
            return 100;
        }        
        if (judge === PlayState.tied) {
            this.boardData.chessState = ChessState.end;
            return 101;
        }
        // todo 如果是跟ai下棋，通知ai行棋
        this.turnPlayer();
        return 99;
    }

    /**
     * 放置棋子
     * @param positionX
     * @param positionY
     * @param pieceColor
     */
    doPlacingPiece(positionX, positionY, pieceColor) {
        this.boardData.pointers[positionX][positionY] = pieceColor;
        this.boardData.places.push({positionX, positionY, pieceColor});
    }

    /**
     * 刷新棋盘数据
     */
    refreshBordData() {
        this.boardData.chessState = ChessState.init;
        this.boardData.currentTurn = this.userInfo.holderColor;
        this.boardData.pointers = new Array(this.boardData.xLines);
        for (let i = 0; i < this.boardData.yLines; i += 1) {
            this.boardData.pointers[i] = new Array(this.boardData.yLines);
            for (let j = 0; j < this.boardData.xLines; j += 1) {
                this.boardData.pointers[i][j] = ChessPieceColor.empty;
            }
        }
        this.boardData.places = new Array(this.boardData.xLines * this.boardData.yLines);
    }

    /**
     * 切换对手
     */
    turnPlayer() {
        if (this.boardData.currentTurn === ChessPieceColor.black) {
            this.boardData.currentTurn = ChessPieceColor.white;
        } else {
            this.boardData.currentTurn = ChessPieceColor.black;
        }
    }

    /**
     * 开始游戏
     */
    startGame() {
        // todo 开局信息传给服务器
        this.boardData.chessState = ChessState.start;
    }

    /**
     * 落子
     * @param positionX
     * @param positionY
     */
    placingPieceServer(positionX, positionY) {
        // todo 开局信息传给服务器
    }

    /**
     * 刷新棋盘数据
     */
    refreshBordDataServer() {
        // todo 开局信息传给服务器
    }

    /**
     * 胜负判定
     * @param positionX
     * @param positionY
     * @returns {boolean}
     */
    win(positionX, positionY) {

        let currentColor = this.boardData.pointers[positionX][positionY];
        let count = 0;
        let tempX;
        let tempY;

        // x 方向
        for (let i = positionX; i < positionX + this.boardData.winNum && i < this.boardData.xLines; i++) {
            if (this.boardData.pointers[i][positionY] === currentColor) {
                tempX = i;
            } else {
                break;
            }
        }
        for (let i = tempX; i > tempX - this.boardData.winNum && i >= 0; i--) {
            if (this.boardData.pointers[i][positionY] === currentColor) {
                count += 1;
            } else {
                break;
            }
        }
        if (count >= this.boardData.winNum) {
            return PlayState.win;
        }

        // y 方向
        count = 0;
        for (let i = positionY; i < positionY + this.boardData.winNum && i < this.boardData.yLines; i++) {
            if (this.boardData.pointers[positionX][i] === currentColor) {
                tempY = i;
            } else {
                break;
            }
        }
        for (let i = tempY; i > tempY - this.boardData.winNum && i >= 0; i--) {
            if (this.boardData.pointers[positionX][i] === currentColor) {
                count += 1;
            } else {
                break;
            }
        }
        if (count >= this.boardData.winNum) {
            return PlayState.win;
        }

        // xy45度 方向
        count = 0;
        for (let i = positionX, j = positionY; i < positionX + this.boardData.winNum && i < this.boardData.xLines && j >= 0; i++, j--) {
            if (this.boardData.pointers[i][j] === currentColor) {
                tempX = i;
                tempY = j;
            } else {
                break;
            }
        }
        for (let i = tempX, j = tempY; i > tempX - this.boardData.winNum && i >= 0 && j < this.boardData.yLines; i--, j++) {
            if (this.boardData.pointers[i][j] === currentColor) {
                count += 1;
            } else {
                break;
            }
        }
        if (count >= this.boardData.winNum) {
            return PlayState.win;
        }

        // xy135度 方向
        count = 0;
        for (let i = positionX, j = positionY; i < positionX + this.boardData.winNum && i < this.boardData.xLines && j < this.boardData.yLines; i++, j++) {
            if (this.boardData.pointers[i][j] === currentColor) {
                tempX = i;
                tempY = j;
            } else {
                break;
            }
        }
        for (let i = tempX, j = tempY; i > tempX - this.boardData.winNum && i >= 0 && j >= 0; i--, j--) {
            if (this.boardData.pointers[i][j] === currentColor) {
                count += 1;
            } else {
                break;
            }
        }
        if (count >= this.boardData.winNum) {
            return PlayState.win;
        }
        // 平局
        count = 0;
        for (let i = 0; i < this.boardData.xLines; i++) {
            for (let j = 0; j < this.boardData.xLines; j++) {
                if (this.boardData.pointers[i][j] === ChessPieceColor.empty) {
                    count += 1;
                }
            }
        }
        if (count === 0) {
            return PlayState.tied;
        }
        return PlayState.unClear;
    }
}

export {
    Controller,
}
