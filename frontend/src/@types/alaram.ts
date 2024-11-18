export type AlarmActionType = "REVIEW_COMPLETE" | "REVIEW_URGE" | "MATCH_COMPLETE" | "MATCH_FAIL";

export interface AlarmCount {
  count: number;
}

export interface AlarmItemData {
  alarmId: number;
  actionType: AlarmActionType;
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
  alarmType: "USER";
}

export interface ReviewReminderAlarm {
  roomId: number;
  reviewerId: number;
}
