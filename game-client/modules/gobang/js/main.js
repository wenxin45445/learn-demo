/** 五子棋 main */
/** 依赖导入 */
// import {
//   Controller,
// } from './controller_new';

import {
  Renderer,
} from './renderer_new';

function doPlacingPieces() {
  // todo
}

/**
 * 五子棋主函数
 */
function main() {
  const canvas = document.getElementById('app-canvas');
  // 绘制棋盘
  // new Controller(canvas).drawChessBord();

  // 棋盘边宽
  const chessBorder = 15;
  // 棋盘高度
  const chessHeight = 450;
  // 棋盘宽度
  const chessWeight = 450;
  // 棋盘 x线数
  const xLines = 14;
  // 棋盘 y线数
  const yLines = 14;
  // 棋子大小 相对棋盘间隔的比例
  const pieceRatio = 0.4;
  const renderer = new Renderer(canvas, chessBorder, chessHeight, chessWeight, xLines, yLines, pieceRatio);
  renderer.drawChessBord();
  /**
   * 画布鼠标点击事件处理
   */
  canvas.onclick = (e) => {
    // 相对于棋盘左上角的x坐标, 取位置较近的棋盘的x点
    const positionX = Math.floor(e.offsetX / renderer.spaceX);
    // 相对于棋盘左上角的y坐标, 取位置较近的棋盘的y点
    const positionY = Math.floor(e.offsetY / renderer.spaceY);
    // 先进行接口请求，如果合法点击，在进行绘制棋子
    const result = doPlacingPieces(positionX, positionY);
    // 落子成功，绘制棋子
    if (result === 0) {
      renderer.drawChessPiece(positionX, positionY, renderer.pieceSize, 1);
    }
  };
}

/**
 * 运行主函数入口
 */
window.onload = () => {
  main();
};
