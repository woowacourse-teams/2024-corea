import Icon from "../icon/Icon";
import * as S from "./AlarmButton.style";
import React from "react";
import { Link, useLocation } from "react-router-dom";
import { useFetchAlarmCount } from "@/hooks/queries/useFetchAlarm";
import { theme } from "@/styles/theme";

const AlarmButton = () => {
  const { pathname } = useLocation();
  const isMain = pathname === "/";
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const { data: alarmCountData } = useFetchAlarmCount(isLoggedIn);

  if (!isLoggedIn) return null;

  return (
    <Link to="/alarm">
      <S.AlarmIconWrapper $isMain={isMain}>
        <Icon kind="notificationBell" size="2.8rem" color={theme.COLOR.grey3} />
        {alarmCountData && alarmCountData.count > 0 && (
          <S.Count>{alarmCountData.count >= 10 ? "9+" : alarmCountData.count}</S.Count>
        )}
      </S.AlarmIconWrapper>
    </Link>
  );
};

export default AlarmButton;
