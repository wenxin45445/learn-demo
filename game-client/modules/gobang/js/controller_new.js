/** 导入依赖 */
import {
  Renderer,
} from './renderer_new';


const EState = {
  init: 0,
  start: 1,
  waitPlayer: 2,
  waitAI: 3,
  end: 4,
};

/**
 * 棋盘控制器
 */
class Controller {
  constructor(canvas) {
    this.canvas = canvas;
    this.renderer = new Renderer(this);

    canvas.onmouseup = () => {
      this.pointer.x = NaN;
      this.pointer.y = NaN;
    };
  }

  /** 绘制棋盘 */
  drawChessBord() {
    this.renderer.drawChessBord();
  }
}

export {
  Controller,
  EState,
};
