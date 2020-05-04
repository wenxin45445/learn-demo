/** 棋盘绘制脚本 * */
import {ChessPieceColor} from "./goBangEnum";

/** 渲染类，管理canvas绘图 */
class Renderer {
    /**
     * canvas 管理器构造函数
     * @param canvas 画布
     * @param chessBorder 棋盘边宽
     * @param chessHeight 棋盘高度
     * @param chessWeight 棋盘宽度
     * @param xLines 棋盘 x线数
     * @param yLines 棋盘 y线数
     * @param pieceRatio 棋子大小 相对棋盘间隔的比例
     */
    constructor(canvas, chessBorder, chessHeight, chessWeight, xLines, yLines, pieceRatio) {
        this.canvas = canvas;
        this.context = this.canvas.getContext('2d');
        this.chessBorder = chessBorder;
        this.chessHeight = chessHeight;
        this.chessWeight = chessWeight;
        this.xLines = xLines;
        this.yLines = yLines;
        this.pieceRatio = pieceRatio;
        // x线间隔
        this.spaceX = (chessWeight - chessBorder * 2) / (xLines -1);
        // y线间隔
        this.spaceY = (chessHeight - chessBorder * 2) / (yLines - 1);
        // 棋子大小
        this.pieceSize = this.spaceX < this.spaceY ? this.spaceX * this.pieceRatio : this.spaceY * this.pieceRatio;
    }

    /**
     * 绘制棋盘
     */
    drawChessBord() {
        this.canvas.setAttribute('height', this.chessHeight);
        this.canvas.setAttribute('width', this.chessWeight);
        this.context.beginPath();
        this.context.strokeStyle = '#2b2b2b';

        // 垂直方向画xLines根线，相距spaceX px
        for (let i = 0; i < this.xLines; i += 1) {
            this.context.moveTo(this.chessBorder + i * this.spaceX, this.chessBorder);
            this.context.lineTo(this.chessBorder + i * this.spaceX, this.chessWeight - this.chessBorder);
        }
        // 水平方向画yLines根线，相距spaceY px
        for (let i = 0; i < this.yLines; i += 1) {
            this.context.moveTo(this.chessBorder, this.chessBorder + this.spaceY * i);
            this.context.lineTo(this.chessWeight - this.chessBorder, this.chessBorder + this.spaceY * i);
        }
        this.context.stroke();
        this.context.closePath();
    }

    /**
     * 绘制棋子
     * @param positionX x坐标
     * @param positionY y坐标
     * @param pieceSize 棋子大小
     * @param pieceColor 棋子颜色 0：白子 1：黑子
     */
    drawChessPiece(positionX, positionY, pieceSize, pieceColor) {
        this.context.beginPath();
        // 绘制棋子
        this.context.arc(this.chessBorder + positionX * this.spaceX, this.chessBorder + positionY * this.spaceY, this.pieceSize, 0, 2 * Math.PI);
        // 设置渐变
        const morphing = this.context.createRadialGradient(this.chessBorder + positionX * this.spaceX,
            this.chessBorder + positionY * this.spaceY, this.pieceSize,
            this.chessBorder + positionX * this.spaceX,
            this.chessBorder + positionY * this.spaceY,
            0);
        if (pieceColor === ChessPieceColor.black) {
            // 黑棋
            morphing.addColorStop(0, '#0A0A0A');
            morphing.addColorStop(1, '#636766');
        } else if (pieceColor === ChessPieceColor.white) {
            // 白棋
            morphing.addColorStop(0, '#D1D1D1');
            morphing.addColorStop(1, '#F9F9F9');
        } else {
            alert('no this color');
        }
        this.context.fillStyle = morphing;
        this.context.fill();
        this.context.closePath();
    }
}

export {
    Renderer,
};
