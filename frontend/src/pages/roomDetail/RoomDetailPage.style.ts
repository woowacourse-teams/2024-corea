import styled from "styled-components";
import media from "@/styles/media";

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5rem;

  ${media.small`
    gap: 2rem;
  `}
`;

export const FeedbackContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: space-between;

  ${media.small`
    display: flex;
    gap: 2rem;  
    flex-direction: column;
  `}
`;

export const FeedbackSection = styled.div`
  width: 100%;

  ${media.medium`
    width: calc(50% - 1rem); 
  `}

  ${media.large`
    width: calc(50% - 1rem); 
  `}
`;

export const StyledDescription = styled.p`
  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey3};
`;
