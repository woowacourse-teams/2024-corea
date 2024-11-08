import styled from "styled-components";

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6rem;
`;

export const ImgWithError = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;

  img {
    width: 50%;
    max-width: 200px;
  }

  p {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }
`;

export const FeedbackContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: space-between;

  @media screen and (width < 810px) {
    flex-direction: column;
    gap: 6rem;
  }
`;

export const FeedbackSection = styled.div`
  width: 100%;
`;

export const StyledDescription = styled.p`
  height: fit-content;
  font: ${({ theme }) => theme.TEXT.semiSmall};
  line-height: 2rem;
  color: ${({ theme }) => theme.COLOR.grey3};
`;

export const ToggleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
