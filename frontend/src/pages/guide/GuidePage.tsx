import { Link } from "react-router-dom";
import CodeBlock from "@/components/codeGuide/codeBlock/CodeBlock";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/pages/guide/GuidePage.style";
import {
  code_reviewe_bad,
  code_reviewe_good,
  gihub_pr_ex,
  github_comment,
  github_comment_pending,
  github_pr,
  github_suggestion,
  github_suggestion_ex,
} from "@/assets";

const guidePageOptions = [
  {
    title: "BACKEND",
    link: "https://code-review-area.notion.site/v-1-0-b2ea7761e0e949e396c3bdd45860d270?pvs=4",
  },
  { title: "FRONTEND", link: "http://code-review-area.notion.site" },
];

const GuidePage = () => {
  return (
    <S.GuidPageLayout>
      <ContentSection title="분야별 코드 작성 가이드 바로가기">
        <S.GuideContainer>
          {guidePageOptions.map((option) => (
            <Link to={option.link} target="_blank" key={option.link}>
              <S.CardContainer>
                <Icon kind="link" size="2rem" />
                <span>{option.title}</span>
              </S.CardContainer>
            </Link>
          ))}
        </S.GuideContainer>
      </ContentSection>

      <ContentSection title="코드 리뷰를 위한 가이드 바로보기">
        <S.StyledContainer>
          <h1>📑 코드리뷰란?</h1>
          <S.StyledSquare>
            코드리뷰는 다른 개발자가 작성한 코드를 검토하고 피드백을 주고받는 과정이에요. <br />
          </S.StyledSquare>
          <p>
            작성자가 인지하지 못했던 코드의 문제를 사전에 발견하고, 더 나은 방법을 제안하여
            궁극적으로는 작성자가 코드의 품질을 개선할 수 있도록 돕는 일이죠. <br />
            하지만 여러 명이 함께 작업하는 만큼 함께 지켜나가면 좋은 에티켓들도 있는데요. <br />
            <br />이 페이지에서는 저희 팀의 코드 리뷰 경험을 바탕으로 여러분에게 도움이 될 수도 있을
            팁을 몇 가지 소개해드리려고 해요.
          </p>
        </S.StyledContainer>

        <S.StyledContainer>
          <h1>🛠️ 알아두면 편한 GitHub 기능들</h1>

          <h2>1. 코드 리뷰는 어떻게 시작하나요?</h2>
          <img src={github_pr} alt="GitHub PR 화면" />
          <p>
            열려 있는 <span>Pull Request(PR)</span>를 통해 코드 리뷰를 시작할 수 있어요.
          </p>
          <ul>
            <li>
              <S.StyledCodeLine>Commits</S.StyledCodeLine> 탭 : 커밋 기록 확인
            </li>
            <li>
              <S.StyledCodeLine>Files changed</S.StyledCodeLine> 탭 : 코드 변화 확인
            </li>
          </ul>

          <h2>2. 리뷰 코멘트는 어떻게 다나요?</h2>
          <img src={github_comment} alt="GitHub 코멘트 기능" />
          <ul>
            <li>
              원하는 코드 라인에 마우스 <span className="underline">커서</span>를 가져다 대면
              플러스(+) 버튼이 노출돼요.
            </li>
            <li className="second">버튼을 클릭하면 해당 코드에 대한 코멘트를 작성할 수 있어요.</li>
            <li>드래그하여 여러 줄의 코드를 한 번에 선택할 수도 있어요.</li>
            <li className="second">
              여러 줄에 걸쳐 구현된 특정 로직에 대한 피드백을 남기는 데 유용해요.
            </li>
          </ul>

          <h2>3. Suggestion 코멘트를 달고 싶어요!</h2>
          <img src={github_suggestion} alt="GitHub Suggestion 기능" />
          <p>
            코드 변경 제안을 할 때, 리뷰어가 제안하는 코드를 <span>suggestion</span> 기능을 활용해
            직접 보여줄 수 있어요.
          </p>
          <ul>
            <li>Files changed 탭에서만 노출되는 버튼이에요.</li>
          </ul>
          <img src={github_suggestion_ex} alt="Suggestion 예시" />

          <h2>4. 제 코멘트가 pending 상태에서 넘어가지 않아요!</h2>
          <img src={github_comment_pending} alt="Pending 상태의 코멘트" />
          <ul>
            <li>
              코멘트만 남긴 상태에서는 코멘트 옆에서 노란색
              <S.StyledCodeLine>Pending</S.StyledCodeLine> 마크가 떠있어요.
            </li>
            <li className="second">이 상태에서는 리뷰이가 리뷰 내용을 확인할 수 없어요.</li>
            <li>
              리뷰를 완료한 후 리뷰를 전달하기 위해서는 우측 상단에서
              <S.StyledCodeLine>Review changes</S.StyledCodeLine> 버튼을 클릭해야 해요.
            </li>
            <li className="second">
              리뷰 종류(<S.StyledCodeLine>Request Changes</S.StyledCodeLine>,
              <S.StyledCodeLine>Approve</S.StyledCodeLine>,
              <S.StyledCodeLine>Comment</S.StyledCodeLine>)를 선택하고
            </li>
            <li className="second">
              <S.StyledCodeLine>Submit review</S.StyledCodeLine> 버튼을 누르면 모든 리뷰가 완료돼요.
            </li>
          </ul>
        </S.StyledContainer>

        <S.StyledContainer>
          <h1>🤔 고려하면 좋은 요소들 - 공통</h1>
          <p>코드 리뷰는 리뷰이의 코드가 클린 코드에 더 가까워질 수 있도록 도와주는 역할이에요.</p>
          <ul>
            <li>
              코드 작성 가이드와&nbsp;
              <a href="https://meetup.nhncloud.com/posts/106" target="_blank" rel="noreferrer">
                커밋 가이드라인
              </a>
              을 준수하여 코드의 일관성을 유지하는 것을 목표로 해요.
            </li>
            <li>
              코딩 컨벤션을 철저히 지켜 가독성을 높이고, 다른 개발자들이 코드를 이해하기 쉽게 하는
              게 좋아요.
            </li>
            <li>리뷰이는 위와 같은 코드를 작성하기 위해 노력하면 좋아요.</li>
            <li> 리뷰어는 위와 같은 코드로 발전할 수 있는 제안을 하면 좋아요.</li>
          </ul>
        </S.StyledContainer>

        <S.StyledContainer>
          <h1>🤔 고려하면 좋은 요소들 - 리뷰이</h1>
          <h2>PR 메시지는 분명하게</h2>
          <img src={gihub_pr_ex} alt="PR 메시지 예시" className="medium" />
          <p>
            리뷰이는 PR 메시지를 통해 작성된 코드의 목적과 내용을 명확하게 설명할 수 있어요. 이는
            리뷰어가 코드를 쉽게 이해하고, 더 나은 피드백을 제공하는 데 도움이 돼요.
          </p>
          <p>PR 메시지에는 다음과 같은 요소가 포함되면 좋아요.</p>
          <ul>
            <li>
              코드를 작성하며 느꼈던 난이도 (e.g. <em>쉬웠어요, 어려웠어요, 복잡했어요...</em>)
            </li>
            <li>
              로직의 흐름(e.g. <em>이 로직은 이런 흐름으로 작성되었어요.</em>)
            </li>
            <li>
              고민한 부분(e.g. <em>이 부분은 어떻게 개선할 수 있을까요? 이런 로직이 괜찮은가요?</em>
              )
            </li>
          </ul>
          <S.IconWrapper>
            <Icon kind="link" size="2rem" />
            <a
              href="https://github.com/woowacourse/java-chess/pull/699"
              target="_blank"
              rel="noreferrer"
            >
              참고 가능한 PR 링크
            </a>
          </S.IconWrapper>
        </S.StyledContainer>

        <S.StyledContainer>
          <h1>🤔 고려하면 좋은 요소들 - 리뷰어</h1>
          <h2>1. 코드 리뷰 시 주로 확인하면 좋은 것들</h2>
          <S.IconWrapper>
            <Icon kind="link" size="2rem" />
            <a
              href="https://google.github.io/eng-practices/review/reviewer/looking-for.html"
              target="_blank"
              rel="noreferrer"
            >
              구글 코드 리뷰 가이드
            </a>
          </S.IconWrapper>
          <ul>
            <li>
              처음부터 답을 알려주기보다는 고민할 방향을 제시해주는 게 리뷰이의 장기적인 성장에 더
              큰 도움이 돼요.
            </li>
            <li>
              <em>유지보수성, 재사용성, 안정성, 확장성, 테스트 구성</em> 등의 핵심 가치가 잘
              지켜지고 있는지 확인해주세요.
            </li>
            <li className="second">예시</li>
            <li className="third">하드 코딩된 값은 없나요?</li>
            <li className="third">중복 코드는 없나요?</li>
            <li className="third">예외 처리가 제대로 되고 있나요?</li>
            <li className="third">하나의 메서드가 하나의 일만 하고 있나요?</li>
            <li className="third">코드를 이해하기 쉬운가요?</li>
            <li className="third">컨벤션은 잘 지켜지고 있나요?</li>
          </ul>

          <h2>2. 예시를 들어 설명하기</h2>
          <p>
            피드백을 줄 때는 가능하면 구체적인 예시를 들어 설명해주세요. <span>suggestion</span>
            기능을 활용하면 좋아요!
          </p>

          <p>코드 예시</p>

          <CodeBlock>
            {`public double calculateDiscount(double price, boolean isMember, boolean isHoliday) {
    if (isMember) {
        if (isHoliday) {
              return price * 0.2; // 회원이고 공휴일인 경우
            } else {
                  return price * 0.1; // 회원이고 공휴일이 아닌 경우
                }
              } else {
                  if (isHoliday) {
                      return price * 0.05; // 비회원이고 공휴일인 경우
                  } else {
                    return 0; // 비회원이고 공휴일이 아닌 경우
            }
      }
}`}
          </CodeBlock>
          <p>리뷰 코드 예시</p>
          <CodeBlock>
            {`public double calculateDiscount(double price, boolean isMember, boolean isHoliday) {
    double discountRate = getDiscountRate(isMember, isHoliday);
    return price * discountRate;
}

private double getDiscountRate(boolean isMember, boolean isHoliday) {
    if (isMember) {
        return isHoliday ? 0.2 : 0.1;
    } else {
        return isHoliday ? 0.05 : 0;
    }
}`}
          </CodeBlock>

          <h2>3. 신뢰할 수 있는 링크 첨부</h2>
          <ul>
            <li>
              특정 기술이나 방법론에 대해 피드백 할 때 참고 자료를 첨부하면 리뷰이가 추가 학습을
              통해 더 나은 코드를 작성할 수 있어요.
            </li>
            <li>
              구글 블로그 링크보다는 공식 문서와 같이 신뢰할 수 있는 출처에서 발행한 문서가 더
              좋아요.
            </li>
          </ul>

          <h2>4. 커뮤니케이션</h2>
          <ul>
            <li>
              코드리뷰는 단순한 오류 지적이 아니라, 상호 협업과 성장을 위한 커뮤니케이션임을
              기억해주세요!
            </li>
            <li>
              지나치게 날카로운 말투나 명령조의 리뷰는 상대의 기분을 상하게 할 수 있어요. 긍정적이고
              건설적인 피드백을 주고받음으로써 서로의 성장을 돕는 문화를 만들어 갈 수 있다면
              좋겠어요.
            </li>
          </ul>

          <h4>좋은 예시</h4>
          <img src={code_reviewe_good} alt="좋은 코드 리뷰 예시" className="medium" />

          <h4>나쁜 예시</h4>
          <img src={code_reviewe_bad} alt="나쁜 코드 리뷰 예시" className="medium" />
        </S.StyledContainer>

        <S.StyledContainer>
          <h1>💡 추가로 참고하면 좋은 자료</h1>
          <S.IconWrapper>
            <Icon kind="link" size="2rem" />
            <a href="https://tech.kakao.com/posts/498" target="_blank" rel="noreferrer">
              효과적인 코드리뷰를 위한 리뷰어의 자세(카카오)
            </a>
          </S.IconWrapper>
          <S.IconWrapper>
            <Icon kind="link" size="2rem" />
            <a
              href="https://github.com/meshkorea/front-end-engineering/blob/main/conventions/code-review/index.md"
              target="_blank"
              rel="noreferrer"
            >
              메쉬코리아 팀의 코드 리뷰 규칙
            </a>
          </S.IconWrapper>
        </S.StyledContainer>
      </ContentSection>
    </S.GuidPageLayout>
  );
};

export default GuidePage;
