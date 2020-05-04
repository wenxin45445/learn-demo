/**
 * 对弈状态
 * @type {{init: number, start: number, end: number, pause: number}}
 */
const ChessState = {
    // 初始化
    init: 0,
    // 对弈中
    start: 1,
    // 暂停
    pause: 2,
    // 已结束
    end: 3,
};
/**
 * 房间模式
 */
const RoomModule = {
    // 自我对弈
    self: 0,
    // 人机对弈
    ai: 1,
    // 在线玩家对弈
    player: 2,
}

/**
 * 网络模式
 */
const NetModule = {
    // 在线
    onLine: 0,
    // 离线
    offLine: 1,
}

/**
 * 棋子颜色
 * @type {{white: number, black: number, error: undefined, empty: number}}
 */
const ChessPieceColor = {
    empty: 0,
    black: -1,
    white: 1,
};

/**
 * 用户信息
 */
class UserInfo {
    constructor(id, holderColor) {
        this.id = id;
        this.holderColor = holderColor;
    }
}
/**
 * 房间信息
 */
class RoomInfo {
    constructor(players) {
        // 玩家列表
        this.players = players;
    }
}

/**
 * 导出
 */
export {
    ChessState,
    RoomModule,
    ChessPieceColor,
    UserInfo,
    NetModule,
}