/**
 * 棋盘数据
 */
import {ChessPieceColor} from "./goBangEnum";

class BordData {
    constructor(chessState, roomModule, xLines, yLines, winNum, firstColor, netModule) {
        // 对弈状态
        this.chessState = chessState;
        // 房间模式
        this.roomModule = roomModule;
        // x线数量
        this.xLines = xLines;
        // y线数量
        this.yLines = yLines;
        // 多少连子为赢
        this.winNum = winNum;
        // 当前轮到谁下棋
        this.currentTurn = firstColor;
        // 棋盘上棋子状态
        this.pointers = new Array(xLines);
        for (let i = 0; i < yLines; i += 1) {
            this.pointers[i] = new Array(yLines);
            for (let j = 0; j < xLines; j += 1) {
                this.pointers[i][j] = ChessPieceColor.empty;
            }
        }
        // 每下一个点的数据
        this.places = new Array(xLines * yLines);
        this.netModule = netModule;
    }
}

/**
 * 导出
 */
export {
    BordData
}