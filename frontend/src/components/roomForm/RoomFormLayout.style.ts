import styled from "styled-components";
import media from "@/styles/media";

export const CreateSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  align-items: center;

  width: 100%;
  margin-bottom: 20rem;
  padding: 4rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const RowContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: flex-start;

  width: 100%;

  ${media.large`
    flex-direction: row;
    align-items: center;
  `}
`;

export const ContentLabel = styled.span`
  flex-shrink: 0;
  width: 250px;
  font: ${({ theme }) => theme.TEXT.medium_bold};

  span {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    color: ${({ theme }) => theme.COLOR.error};
  }
`;

export const ContentInput = styled.div`
  display: flex;
  gap: 1rem;
  width: 100%;
`;

export const ContentWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
  align-items: center;

  font: ${({ theme }) => theme.TEXT.medium};
`;

export const ContentRadioInput = styled.input`
  display: none; /* 기본 라디오 버튼은 숨김 */
`;

export const RadioLabel = styled.label`
  cursor: pointer;

  position: relative;

  padding-left: 2.5rem;

  font-size: 1rem;
  color: ${({ theme }) => theme.COLOR.grey2};

  /* 라디오 버튼 원 모양 스타일링 */
  &::before {
    content: "";

    position: absolute;
    top: 50%;
    left: 0;
    transform: translateY(-50%);

    width: 1.5rem;
    height: 1.5rem;

    background-color: ${({ theme }) => theme.COLOR.white};
    border: 2px solid ${({ theme }) => theme.COLOR.grey3};
    border-radius: 50%;

    transition: 0.2s all ease-in-out;
  }

  /* 선택된 상태에서 스타일링 */
  input:checked + &::before {
    background-color: ${({ theme }) => theme.COLOR.primary2};
    border-color: ${({ theme }) => theme.COLOR.primary2};
  }

  /* 체크된 상태에서 원 안의 점 */
  input:checked + &::after {
    content: "";

    position: absolute;
    top: 50%;
    left: 0.6rem;
    transform: translateY(-50%);

    width: 0.75rem;
    height: 0.75rem;

    background-color: ${({ theme }) => theme.COLOR.white};
    border-radius: 50%;
  }
`;
