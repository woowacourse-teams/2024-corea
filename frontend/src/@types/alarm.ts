export type AlarmActionType =
  | "REVIEW_COMPLETE"
  | "REVIEW_URGE"
  | "MATCH_COMPLETE"
  | "MATCH_FAIL"
  | "FEEDBACK_CREATED";

export type AlarmSubjectType = "USER" | "SERVER";

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
  alarmType: AlarmSubjectType;
}

export interface AlarmListData {
  data: AlarmItemData[];
}

export interface AlarmAsRead {
  alarmId: number;
  alarmType: AlarmSubjectType;
}

export interface ReviewReminderAlarm {
  roomId: number;
  reviewerId: number;
}
