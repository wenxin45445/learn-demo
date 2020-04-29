/** 五子棋 main */
/** 依赖导入 */
import {
  Controller,
} from './controller_new';

/**
 * 五子棋主函数
 */
function main() {
  const canvas = document.getElementById('app-canvas');
  // 绘制棋盘
  new Controller(canvas).drawChessBord();
}

/**
 * 运行主函数入口
 */
window.onload = () => {
  main();
};
