import styled from "styled-components";
import { EllipsisText } from "@/styles/common";
import media from "@/styles/media";

export const ProfileCardContainer = styled.div`
  overflow: hidden;

  width: 100%;
  padding: 1rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const ProfileTitle = styled.div`
  padding: 1rem 0 0 1rem;
  font: ${({ theme }) => theme.TEXT.medium_bold};
`;

export const ProfileCardWrapper = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;
  justify-content: space-between;

  ${media.small`
    gap: 2rem;
    flex-direction: column;
  `}
`;

export const ProfileWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  margin: 0 auto;
  padding: 2rem;
`;

export const ProfileNickname = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;

  max-width: 150px;
`;

export const GithubIcon = styled.div`
  flex-shrink: 0;
`;

export const NicknameText = styled.span`
  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ theme }) => theme.COLOR.grey3};
  ${EllipsisText}
`;

export const ProfileSummaryContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;

  width: 150px;
  margin: 0 auto;
  padding: 0;

  font: ${({ theme }) => theme.TEXT.medium};

  ${media.small`
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    word-break: keep-all;
  `}

  dl {
    display: flex;
    justify-content: space-between;

    ${media.small`
      gap: 1rem;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    `}
  }

  dt {
    font: ${({ theme }) => theme.TEXT.small};
    color: ${({ theme }) => theme.COLOR.grey4};
    ${media.small`
      font: ${({ theme }) => theme.TEXT.semiSmall};
    `}
  }

  dd {
    display: flex;
    gap: 0.1rem;
    align-items: center;
    font: ${({ theme }) => theme.TEXT.small_bold};
  }
`;

export const KeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  width: fit-content;
  margin: 0 auto;
  padding: 1rem;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
  font: ${({ theme }) => theme.TEXT.small};

  svg {
    flex-shrink: 0;
  }
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
