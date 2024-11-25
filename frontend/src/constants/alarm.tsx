type AlarmMessageProps = {
  username?: string;
  info: string;
};

const ALARM_MESSAGES = {
  REVIEW_COMPLETE: ({ username, info }: AlarmMessageProps) => {
    return (
      <>
        <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>코드리뷰를 완료</span>
        했습니다.
      </>
    );
  },

  REVIEW_URGE: ({ username, info }: AlarmMessageProps) => {
    return (
      <>
        <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>코드리뷰를 재촉</span>
        했습니다.
      </>
    );
  },

  FEEDBACK_CREATED: ({ username, info }: AlarmMessageProps) => {
    return (
      <>
        <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>피드백을 작성</span>
        했습니다.
      </>
    );
  },

  MATCH_COMPLETE: ({ info }: AlarmMessageProps) => {
    return (
      <>
        <span>{info}</span> 미션에 대해 <span>매칭이 완료</span> 됐습니다.
      </>
    );
  },

  MATCH_FAIL: ({ info }: AlarmMessageProps) => {
    return (
      <>
        <span>{info}</span> 미션에 대해 <span>매칭이 실패</span> 했습니다.
      </>
    );
  },
};

export default ALARM_MESSAGES;
