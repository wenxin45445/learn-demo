/** 五子棋 main */
/** 依赖导入 */
import {BordData} from './board';
import {Renderer} from './renderer';
import {
    ChessPieceColor,
    ChessState,
    RoomModule,
    UserInfo,
    NetModule,
} from './goBangEnum';
import {Controller} from "./controller";

/**
 * 五子棋主函数
 */
function main() {
    // 获取画布
    const tip = document.getElementById('tip');
    // 获取画布
    const canvas = document.getElementById('app-canvas');
    // 棋盘边宽
    const chessBorder = 15;
    // 棋盘高度
    const chessHeight = 450;
    // 棋盘宽度
    const chessWeight = 450;
    // 棋盘 x线数
    const xLines = 15;
    // 棋盘 y线数
    const yLines = 15;
    // 棋子大小 相对棋盘间隔的比例
    const pieceRatio = 0.4;
    const renderer = new Renderer(canvas, chessBorder, chessHeight, chessWeight, xLines, yLines, pieceRatio);
    // const boardData = new BordData(ChessState.init, RoomModule.self, xLines, yLines, 5, ChessPieceColor.black, NetModule.offLine);
    const boardData = new BordData(ChessState.init, RoomModule.ai, xLines, yLines, 5, ChessPieceColor.black, NetModule.offLine);
    // 在线的时候，在这里把开局信息传给服务器
    const controller = new Controller(boardData, new UserInfo(1, ChessPieceColor.black));
    controller.startGame();
    renderer.drawChessBord();
    /**
     * 画布鼠标点击事件处理
     */
    canvas.onclick = (e) => {
        // 相对于棋盘左上角的x坐标, 取位置较近的棋盘的x点
        const positionX = Math.floor(e.offsetX / renderer.spaceX);
        // 相对于棋盘左上角的y坐标, 取位置较近的棋盘的y点
        const positionY = Math.floor(e.offsetY / renderer.spaceY);
        let currentTurn = boardData.currentTurn;
        let result;
        // 模式判定
        if (boardData.roomModule === RoomModule.self || boardData.roomModule === RoomModule.ai) {
            // 这两种模式, 修改本地数据
            result = controller.placingPiece(positionX, positionY);
            // 离线模式判定
            if (boardData.netModule === NetModule.offLine) {
                // 离线模式，记录也不做
            } else {
                // 远程只做记录
                controller.placingPieceServer(positionX, positionY);
            }
        } else {
            // 请求远程接口， 本地修改数据
            result = controller.placingPieceServer(positionX, positionY);
            controller.placingPiece(positionX, positionY);
        }

        console.log("server result: " + result);

        if (result === 100) {
            // 胜负已定， 提示玩家
            renderer.drawChessPiece(positionX, positionY, renderer.pieceSize, currentTurn);
            let message;
            if (boardData.pointers[positionX][positionY] === ChessPieceColor.white) {
                message = '白棋胜！';
            } else {
                message = '黑棋胜！';
            }
            tip.innerHTML = "<front style='color:red;'>" +  message + "</front>";
        } else if (result === 99) {
            // 成功落子，对应提示动作
            renderer.drawChessPiece(positionX, positionY, renderer.pieceSize, currentTurn);
        } else if (result === 101) {
            // 平棋， 提示玩家
            renderer.drawChessPiece(positionX, positionY, renderer.pieceSize, currentTurn);
            tip.innerHTML = "<front style='color:red;'>" +  "平棋" + "</front>";
        } else {
            // 落子失败，删除棋子
        }


    };
}

/**
 * 运行主函数入口
 */
window.onload = () => {
    main();
};
