import styled from "styled-components";

export const ExpandableSection = styled.div`
  cursor: pointer;
  overflow: hidden;
  transition: all 0.3s ease-in-out;
`;

export const StyledTitle = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 1.4rem 1rem;

  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.grey4};

  background-color: ${({ theme }) => theme.COLOR.primary1};
  border-left: 4px solid ${({ theme }) => theme.COLOR.primary3};
`;

export const ExpandableContent = styled.div`
  padding: 2rem 1rem;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.black};

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-left: 4px solid ${({ theme }) => theme.COLOR.primary3};

  transition: all 0.3s ease-in-out;

  p {
    margin-bottom: 1rem;
    font: ${({ theme }) => theme.TEXT.small};
  }

  ol {
    padding-left: 1rem;
    font: ${({ theme }) => theme.TEXT.small};
  }

  li {
    margin: 0.4rem 1rem;
    font: ${({ theme }) => theme.TEXT.semiSmall};
    list-style-type: auto;
  }
`;
