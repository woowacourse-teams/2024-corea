export interface AlarmCount {
  count: number;
}

export interface AlarmItemData {
  alarmId: number;
  actionType: string;
  actor: {
    memberId: number;
    username: string;
    thumbnailUrl: string;
  };
  interaction: {
    interactionId: number;
    info: string;
  };
  isRead: boolean;
  createAt: string;
  alarmType: string;
}

export interface AlarmListData {
  data: AlarmItemData[];
}

export interface AlarmAsRead {
  alarmId: number;
  alarmType: string;
}
