import styled from "styled-components";

export const ProfileCardContainer = styled.div`
  width: 100%;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 20px;
`;

export const ProfileTitle = styled.div`
  padding: 1rem 0 0 1rem;
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
  text-align: center;
`;

export const ProfileInfoWrapper = styled.div`
  margin: 0 auto;
  padding: 1rem;
`;

export const ProfileInfoTable = styled.table`
  border-spacing: 1rem;
  border-collapse: separate;
`;

export const ProfileFlex = styled.div`
  display: flex;
  align-items: center;
`;

export const KeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  margin: 0 auto;
  padding: 1rem;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;

export const Keyword = styled.div`
  margin-top: 0.5rem;
  padding: 1rem;

  color: ${({ theme }) => theme.COLOR.black};

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 0 10px 10px;
`;

export const AttitudeScoreWrapper = styled.div`
  padding: 1rem;
`;

export const AttitudeScoreText = styled.div`
  padding: 1rem 0 2rem 1rem;
`;
