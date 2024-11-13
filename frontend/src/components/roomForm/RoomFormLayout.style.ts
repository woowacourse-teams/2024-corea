import styled from "styled-components";
import media from "@/styles/media";

export const RoomFormContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  align-items: center;

  width: 100%;
  min-width: 335px;
  max-width: 700px;
  margin: auto;
  margin-bottom: 20rem;
`;

export const SectionTitle = styled.h1`
  display: flex;
  gap: 1rem;
  align-items: flex-end;

  width: 100%;

  font: ${({ theme }) => theme.TEXT.xLarge_bold};
  color: ${({ theme }) => theme.COLOR.grey4};
`;

export const HelpText = styled.p`
  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const SubSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 4rem;

  width: 100%;
  padding: 2rem;

  border: 1px solid ${({ theme }) => theme.COLOR.grey0};
  border-radius: 6px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const RowContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
  align-items: flex-start;

  width: 100%;

  ${media.small`
    flex-direction: column;
  `}
`;

export const ContentLabel = styled.span`
  flex-shrink: 0;
  width: 200px;
  font: ${({ theme }) => theme.TEXT.medium_bold};
  line-height: 30px;

  ${media.large`
    width: 250px;
  `}
`;

export const RequiredLabel = styled.span`
  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ theme }) => theme.COLOR.error};
`;

export const ContentInput = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
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
  display: none;
`;

export const RadioLabel = styled.label`
  cursor: pointer;

  position: relative;

  padding-left: 2.5rem;

  font-size: 1rem;
  color: ${({ theme }) => theme.COLOR.grey2};

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

  input:checked + &::before {
    background-color: ${({ theme }) => theme.COLOR.primary2};
    border-color: ${({ theme }) => theme.COLOR.primary2};
  }

  input:checked + &::after {
    content: "";

    position: absolute;
    top: 50%;
    left: 0.5rem;
    transform: translateY(-50%);

    width: 0.75rem;
    height: 0.75rem;

    background-color: ${({ theme }) => theme.COLOR.white};
    border-radius: 50%;
  }
`;
