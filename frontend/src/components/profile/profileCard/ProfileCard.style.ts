import styled from "styled-components";
import { EllipsisText } from "@/styles/common";

export const ProfileCardContainer = styled.div`
  width: 100%;
  padding: 1rem;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 20px;
`;

export const ProfileTitle = styled.div`
  padding: 1rem 0 0 1rem;
  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const ProfileCardWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
`;

export const ProfileWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  margin: 0 auto;
  padding: 1.5rem;
`;

export const ProfileNickname = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;

  max-width: 108px;
`;

export const GithubIcon = styled.div`
  flex-shrink: 0;
`;

export const NicknameText = styled.span`
  ${EllipsisText}
  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const ProfileInfoWrapper = styled.div`
  margin: 0 auto;
`;

export const ProfileSummaryContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;

  width: 180px;
  margin: 0;
  padding: 0;

  font: ${({ theme }) => theme.TEXT.medium};

  dl {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  dt {
    font: ${({ theme }) => theme.TEXT.small};
    color: ${({ theme }) => theme.COLOR.grey4};
  }

  dd {
    display: flex;
    gap: 0.1rem;
    align-items: center;
    font: ${({ theme }) => theme.TEXT.small_bold};
  }
`;

export const ProfileFlex = styled.div`
  display: flex;
  align-items: center;
`;

export const KeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  margin: 0 auto;
  padding: 1rem;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
  font: ${({ theme }) => theme.TEXT.small};
`;

export const Keyword = styled.div`
  margin-top: 0.5rem;
  padding: 1rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.black};

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 0 10px 10px;
`;

export const AttitudeScoreWrapper = styled.div`
  padding: 0 1rem 1rem;
`;

export const AttitudeScoreText = styled.div`
  padding: 1rem 0 1rem 1rem;
  font: ${({ theme }) => theme.TEXT.medium_bold};

  span {
    padding-left: 1rem;
    font: ${({ theme }) => theme.TEXT.small};
  }
`;
