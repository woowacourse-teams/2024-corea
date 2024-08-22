import styled from "styled-components";

export const ProfileCardContainer = styled.div`
  width: 100%;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 20px;
`;

export const ProfileTitle = styled.div`
  padding: 1rem 0 0 1rem;
  font: ${({ theme }) => theme.TEXT.medium};
`;

export const ProfileCardWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
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
  font: ${({ theme }) => theme.TEXT.semiSmall};
  text-align: center;
`;

export const ProfileInfoWrapper = styled.div`
  margin: 0 auto;
`;

export const InfoTitle = styled.td`
  font: ${({ theme }) => theme.TEXT.medium};
`;

export const InfoCount = styled.span`
  font: ${({ theme }) => theme.TEXT.small};
`;

export const ProfileInfoTable = styled.table`
  border-spacing: 1.4rem;
  border-collapse: separate;
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
  padding: 0.5rem;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.black};

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 0 10px 10px;
`;

export const AttitudeScoreWrapper = styled.div`
  padding: 0 1rem 1rem;
`;

export const AttitudeScoreText = styled.div`
  padding: 1rem 0 1rem 1rem;
  font: ${({ theme }) => theme.TEXT.medium};

  span {
    padding-left: 1rem;
    font: ${({ theme }) => theme.TEXT.semiSmall};
  }
`;
