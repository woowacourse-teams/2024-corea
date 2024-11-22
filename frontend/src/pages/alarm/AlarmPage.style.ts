import styled from "styled-components";
import media from "@/styles/media";

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const AlarmList = styled.ul`
  ${media.small`
    width: 100%;
  `}

  ${media.medium`
    width: 100%;
    min-width: 590px;
    max-width: 800px;
  `}

  ${media.large`
    width: 800px;
  `}
`;

export const AlarmItem = styled.li<{ $isRead: boolean }>`
  cursor: pointer;

  position: relative;

  display: grid;
  grid-template-columns: 40px 1fr;
  gap: 2rem;
  align-items: center;

  width: 100%;
  min-height: 80px;
  padding: 1rem;

  font: ${({ theme }) => theme.TEXT.small};

  background-color: ${({ theme, $isRead }) => $isRead && theme.COLOR.grey0};

  &:hover {
    background-color: ${({ theme }) => theme.COLOR.grey0};
  }
`;

export const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 40px;
  height: 100%;
`;

export const ContentWrapper = styled.div`
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 0.6rem;

  text-align: left;
`;

export const Content = styled.div<{ $isRead: boolean }>`
  width: 100%;

  font: ${({ theme }) => theme.TEXT.small};
  line-height: normal;
  color: ${({ theme, $isRead }) => ($isRead ? theme.COLOR.grey2 : theme.COLOR.grey4)};
  word-break: break-all;

  span {
    font: ${({ theme }) => theme.TEXT.small_bold};
  }
`;

export const TimeStamp = styled.div<{ $isRead: boolean }>`
  width: 100%;
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme, $isRead }) => ($isRead ? theme.COLOR.grey2 : theme.COLOR.grey3)};
  white-space: nowrap;
`;
