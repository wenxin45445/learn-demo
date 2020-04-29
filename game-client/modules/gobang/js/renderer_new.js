/** 棋盘绘制脚本 * */

/**
 * 棋子颜色
 * @type {{white: number, black: number, error: undefined, empty: number}}
 */
const ChessPieceColor = {
  empty: 0,
  black: -1,
  white: 1,
  error: undefined,
};

/** 渲染类，管理canvas绘图 */
class Renderer {
  constructor(controller, canvasSize = 15 * 30) {
    this.controller = controller;
    this.canvas = controller.canvas;
    this.context = controller.canvas.getContext('2d');
    this.canvas.setAttribute('height', canvasSize);
    this.canvas.setAttribute('width', canvasSize);
    this.canvasSize = canvasSize;
  }
}

export {
  Renderer,
  ChessPieceColor,
};
