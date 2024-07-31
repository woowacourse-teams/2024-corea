import styled from "styled-components";

export const ProfileCardContainer = styled.div`
  width: 100%;
  min-height: 30vh;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 20px;
`;

export const ProfileTitle = styled.div`
  padding: 10px 0 0 10px;
`;

export const ProfileCardWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`;

export const ProfileWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 auto;
  padding: 20px;
`;

export const ProfileNickname = styled.div`
  padding-top: 10px;
  text-align: center;
`;

export const ProfileInfoWrapper = styled.div`
  margin: 0 auto;
  padding: 10px;
`;

export const ProfileInfoTable = styled.table`
  border-spacing: 10px;
  border-collapse: separate;
`;

export const ProfileFlex = styled.div`
  display: flex;
  align-items: center;
`;

export const KeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;

  margin: 0 auto;
  padding: 10px;
`;

export const KeywordWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 10px;
`;

export const Keyword = styled.div`
  margin-top: 5px;
  padding: 10px;

  color: ${({ theme }) => theme.COLOR.black};

  background: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 0 10px 10px;
`;

export const AttitudeScoreWrapper = styled.div`
  padding: 10px;
`;

export const AttitudeScoreText = styled.div`
  padding: 10px 0 25px 10px;
`;
