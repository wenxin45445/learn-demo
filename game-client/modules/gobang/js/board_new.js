const ChessState = {
  // 初始化
  init: 0,
  // 对弈中
  start: 1,
  // 等待对手
  waitPlayer: 2,
  // 已结束
  end: 4,
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
 * 棋盘数据
 */
class BordData {
  // todo
}
